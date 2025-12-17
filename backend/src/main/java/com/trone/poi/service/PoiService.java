package com.trone.poi.service;

import com.trone.poi.dto.PoiRequest;
import com.trone.poi.entity.Poi;
import com.trone.poi.repository.PoiRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoiService {
    private PoiRepository poiRepository;

    public PoiService(PoiRepository poiRepository) {
        this.poiRepository = poiRepository;
    }

    public Poi findPoiById(Long id) {
        return poiRepository.findById(id).orElse(null);
    }

    public List<Poi> findAllPoi() {
        return poiRepository.findAll();
    }

    public Poi save(PoiRequest request) {
        return savePoi(request);
    }

    private Poi savePoi(PoiRequest request) {
        Poi poi = new Poi();

        poi.setName(request.getName());
        poi.setLatitude(request.getLatitude());
        poi.setLongitude(request.getLongitude());
        poi.setConstructionYear(request.getConstructionYear());

        poiRepository.save(poi);

        return poi;
    }

    public Poi updatePoi(Long id, PoiRequest request) {
        Poi existingPoi = poiRepository.findById(id).orElse(null);

        if (existingPoi != null) {
            existingPoi.setName(request.getName());
            existingPoi.setLatitude(request.getLatitude());
            existingPoi.setLongitude(request.getLongitude());
            existingPoi.setConstructionYear(request.getConstructionYear());

            poiRepository.save(existingPoi);
        }

        return existingPoi;
    }

    public void deletePoiById(Long id) {
        poiRepository.deleteById(id);
    }
}
