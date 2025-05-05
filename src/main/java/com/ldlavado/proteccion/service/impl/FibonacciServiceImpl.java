package com.ldlavado.proteccion.service.impl;

import com.ldlavado.proteccion.dto.response.FibonacciResponseDTO;
import com.ldlavado.proteccion.entity.Fibonacci;
import com.ldlavado.proteccion.exception.FibonacciBusinessException;
import com.ldlavado.proteccion.repository.FibonacciRepository;
import com.ldlavado.proteccion.service.EmailService;
import com.ldlavado.proteccion.service.FibonacciService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FibonacciServiceImpl implements FibonacciService {

    @Autowired
    private FibonacciRepository fibonacciRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public FibonacciResponseDTO generateSeriesFromTime(String time) {
        log.info("Generating Fibonacci series from time: {}", time);

        LocalTime localTime = parseTime(time);
        int minutes = localTime.getMinute();
        int seconds = localTime.getSecond();

        int seedX = minutes / 10;
        int seedY = minutes % 10;

        log.debug("Extracted seeds: seedX={}, seedY={}", seedX, seedY);

        validateSeeds(seedX, seedY);
        int length = seconds + 2;
        validateLength(length);

        log.debug("Generating sequence with length: {}", length);
        List<Integer> sequence = generateFibonacciSequence(seedX, seedY, length);
        List<Integer> result = excludeSeedsAndSort(sequence);

        log.info("Generated sequence: {}", result);

        saveFibonacciRecord(localTime, minutes, seconds, result);
        sendFibonacciEmail(time, seedX, seedY, result);

        return buildResponse(time, seedX, seedY, result);
    }

    @Override
    public List<FibonacciResponseDTO> getAllFibonacciSeries() {
        log.info("Fetching all generated Fibonacci series from database");
        List<Fibonacci> records = fibonacciRepository.findAll();

        if (records.isEmpty()) {
            log.warn("No Fibonacci series found in database.");
            throw new FibonacciBusinessException("No Fibonacci series found in the database.");
        }

        return records.stream()
                .map(record -> FibonacciResponseDTO.builder()
                        .time(record.getDate().toString())
                        .seeds(extractSeedsFromBeans(record.getBeans()))
                        .sequence(parseSequenceFromString(record.getFibonacciCreated()))
                        .build())
                .collect(Collectors.toList());
    }

    private LocalTime parseTime(String time) {
        try {
            return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss"));
        } catch (Exception e) {
            log.error("Failed to parse time: {}", time, e);
            throw new FibonacciBusinessException("Invalid time format. Expected HH:mm:ss");
        }
    }

    private void validateSeeds(int seedX, int seedY) {
        if (seedX == 0 && seedY == 0) {
            log.warn("Both seeds are zero. Aborting generation.");
            throw new FibonacciBusinessException("Cannot start Fibonacci with both digits as zero.");
        }
    }

    private void validateLength(int length) {
        if (length <= 2) {
            log.warn("Sequence length is too short: {}", length);
            throw new FibonacciBusinessException("Fibonacci sequence length must be greater than 2.");
        }
    }

    private List<Integer> generateFibonacciSequence(int seedX, int seedY, int length) {
        List<Integer> sequence = new ArrayList<>(Arrays.asList(seedX, seedY));
        for (int i = 2; i < length; i++) {
            sequence.add(sequence.get(i - 1) + sequence.get(i - 2));
        }
        return sequence;
    }

    private List<Integer> excludeSeedsAndSort(List<Integer> sequence) {
        return sequence.subList(2, sequence.size())
                .stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    private void saveFibonacciRecord(LocalTime time, int minutes, int seconds, List<Integer> sequence) {
        Fibonacci entity = Fibonacci.builder()
                .date(time)
                .beans(String.valueOf(minutes))
                .length(seconds)
                .fibonacciCreated(sequence.toString())
                .build();
        fibonacciRepository.save(entity);
        log.info("Fibonacci sequence saved to database: ID={}", entity.getFibonacciId());
    }

    private void sendFibonacciEmail(String time, int seedX, int seedY, List<Integer> sequence) {
        String subject = "Technical Test – Your Full Name";
        String body = "Execution time: " + time + "\n"
                + "Seeds: [" + seedX + ", " + seedY + "]\n"
                + "Generated sequence: " + sequence;

        emailService.sendEmail(subject, body);
        log.info("Email sent with Fibonacci results to recipients.");
    }

    private FibonacciResponseDTO buildResponse(String time, int seedX, int seedY, List<Integer> sequence) {
        return FibonacciResponseDTO.builder()
                .time(time)
                .seeds(List.of(seedX, seedY))
                .sequence(sequence)
                .build();
    }

    private List<Integer> extractSeedsFromBeans(String beans) {
        int minute = Integer.parseInt(beans);
        return List.of(minute / 10, minute % 10);
    }

    private List<Integer> parseSequenceFromString(String sequenceStr) {
        return Arrays.stream(sequenceStr.replaceAll("[\\[\\]\\s]", "").split(","))
                .filter(s -> !s.isBlank())
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
