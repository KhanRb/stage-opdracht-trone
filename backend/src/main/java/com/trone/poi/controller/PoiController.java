package com.trone.poi.controller;

import com.trone.poi.dto.PoiRequest;
import com.trone.poi.entity.Poi;
import com.trone.poi.service.PoiService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/pois")
public class PoiController {
    private PoiService poiService;

    public PoiController(PoiService poiService) {
        this.poiService = poiService;
    }

    // Endpoint to create a new POI
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createPoi(
            // Validate the request body
            @Valid @RequestBody PoiRequest request,
            // Capture any validation errors
            BindingResult bindingResult
            ) {
        {
            Map<String, String> errors = new HashMap<>();

            // Check for validation errors in the request body
            if (bindingResult.hasFieldErrors()) {
                bindingResult.getFieldErrors()
                        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

                return ResponseEntity.badRequest().body(errors);
            }

            // If no errors, proceed to save the POI
            Poi savedPoi = poiService.save(request);

            // // Prepare the response with a success message and the poi ID
            Map<String, String> response = new HashMap<>();
            response.put("message", "POI created successfully");
            response.put("poiId", String.valueOf(savedPoi.getId()));

            return ResponseEntity.ok(response);
        }
    }

    // Endpoint to retrieve all pois
    @GetMapping("/getAll")
    public ResponseEntity<List<Poi>> getAllPois() {
        List<Poi> pois = poiService.findAllPoi();

        return ResponseEntity.ok(pois);
    }

    // Endpoint to retrieve a POI by ID
    @GetMapping("get/{id}")
    public ResponseEntity<?> getPoiById(@PathVariable Long id) {
        Poi findPoi = poiService.findPoiById(id);

        // Check if the POI exists
        if (findPoi == null) {
            Map<String, String> error = new HashMap<>();

            // Prepare the response with an error message
            error.put("message", "POI not found with ID: " + id);

            return ResponseEntity.status(404).body(error);
        }

        return ResponseEntity.ok(findPoi);
    }

    // Endpoint to update a POI
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePoi(
            @PathVariable Long id,
            @Valid @RequestBody PoiRequest request,
            BindingResult bindingResult
    ) {
        Map<String, String> errors = new HashMap<>();

        // Check for validation errors in the request body
        if (bindingResult.hasFieldErrors()) {
            bindingResult.getFieldErrors()
                    .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(errors);
        }

        Poi findPoi = poiService.findPoiById(id);

        // Check if the POI exists
        if (findPoi == null) {
            // Prepare the response with an error message
            errors.put("message", "POI not found with ID: " + id);

            return ResponseEntity.status(404).body(errors);
        }

        Poi updatedPoi = poiService.updatePoi(id, request);

        return ResponseEntity.ok(updatedPoi);
    }

    // Endpoint to delete a POI
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deletePoi(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        Poi findPoi = poiService.findPoiById(id);

        // Check if the POI exists
        if (findPoi == null) {
            // Prepare the response with an error message
            response.put("message", "POI not found with ID: " + id);

            return ResponseEntity.status(404).body(response);
        }

        poiService.deletePoiById(id);

        // Prepare the response with a success message
        response.put("message", "POI deleted successfully");

        return ResponseEntity.ok(response);
    }
}
