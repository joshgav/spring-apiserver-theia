package com.joshgav.apiserver.repository;

import com.joshgav.apiserver.model.Widget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WidgetRepository extends JpaRepository<Widget, String> {}