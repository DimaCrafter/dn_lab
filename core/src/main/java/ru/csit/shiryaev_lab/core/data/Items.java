package ru.csit.shiryaev_lab.core.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.csit.shiryaev_lab.core.entities.Item;


@Repository
public interface Items extends JpaRepository<Item, Integer> {
}
