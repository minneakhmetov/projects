package repositories;

import models.Activity;

import java.util.List;

public interface ActivityRepository {
    List<Activity> getAll();
    Activity getById(Long id);
}
