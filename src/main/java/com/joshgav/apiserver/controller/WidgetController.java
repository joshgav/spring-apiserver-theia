package com.joshgav.apiserver.controller;

import com.joshgav.apiserver.model.Widget;
import com.joshgav.apiserver.repository.WidgetRepository;
import io.opentelemetry.api.trace.Span;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/widgets")
public class WidgetController {
    private static final Logger logger = LoggerFactory.getLogger(WidgetController.class);
    private WidgetRepository widgetRepository;

    @Autowired
    public WidgetController(WidgetRepository widgetRepository) {
        this.widgetRepository = widgetRepository;
    }

    @Operation(summary = "get all widgets", tags = {"widgets"})
    @GetMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Widget>> getWidgets(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        logger.info("widgets requested");
        String traceId = Span.current().getSpanContext().getTraceId();
        String spanId = Span.current().getSpanContext().getSpanId();
        logger.debug("traceId {}, spanId {}", traceId, spanId);

        Pageable pageable = PageRequest.of(page, size);
        Page<Widget> widgetPage = widgetRepository.findAll(pageable);
        return ResponseEntity.ok(widgetPage.getContent());
    }

    @Operation(summary = "get one widget", tags = {"widgets"})
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Widget>> getWidgetById(
            @PathVariable String id
    ) {
        logger.info("widget by ID requested");
        List<Widget> widgets = new ArrayList<Widget>();
        Optional<Widget> widget = widgetRepository.findById(id);
        if (widget.isPresent()) {
            widgets.add(widget.get());
            return ResponseEntity.ok(widgets);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "put one widget", tags = {"widgets"})
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Widget>> putWidget(
            @RequestBody Widget widget, @PathVariable String id
    ) {
        logger.info("put widget requested");
        List<Widget> widgets = new ArrayList<Widget>();
        Widget saved = widgetRepository.save(widget);
        widgets.add(saved);
        return ResponseEntity.ok(widgets);
    }

    @Operation(summary = "put many widgets", tags = {"widgets"})
    @PutMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Widget>> putWidgets(
            @RequestBody Collection<Widget> widgets
    ) {
        logger.info("put widgets requested");
        Collection<Widget> saved = widgetRepository.saveAll(widgets);
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "delete one widget", tags = {"widgets"})
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteWidget(
            @PathVariable String id
    ) {
        logger.info("delete widget requested");
        widgetRepository.deleteById(id);
        return ResponseEntity.accepted().build();
    }
}
