package com.example.demo.service;

import com.example.demo.models.Mood;
import com.example.demo.repository.MoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoodService {

    private final MoodRepository moodRepository;

    public MoodService(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    public List<Mood> findMoodsByUser(Long userId) {
        return moodRepository.findByUserId(userId);
    }
}