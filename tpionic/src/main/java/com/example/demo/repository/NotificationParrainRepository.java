package com.example.demo.repository;

import com.example.demo.model.NotificationParrain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationParrainRepository extends JpaRepository<NotificationParrain, Integer> {
    List<NotificationParrain> findByParentId(int parentId);
    List<NotificationParrain> findByParrainId(int parrainId);
    List<NotificationParrain> findByEnfantId(int enfantId);
}
