package com.example.demo.repository;

import com.example.demo.model.NotificationParrain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationParrainRepository extends JpaRepository<NotificationParrain, Long> {
    List<NotificationParrain> findByParentId(Long parentId);
    List<NotificationParrain> findByParrainId(Integer parrainId);
    List<NotificationParrain> findByEnfantId(Long enfantId);
}
