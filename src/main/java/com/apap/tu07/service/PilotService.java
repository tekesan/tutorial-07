package com.apap.tu07.service;

import java.util.Optional;

import com.apap.tu07.model.PilotModel;

/**
 * PilotService
 */
public interface PilotService {
    Optional<PilotModel> getPilotDetailByLicenseNumber(String licenseNumber);

    PilotModel addPilot(PilotModel pilot);

    void deletePilotByLicenseNumber(String licenseNumber);

    void deletePilot(PilotModel pilot);
    
    
    Optional<PilotModel> getPilotDetailById(long id);
}