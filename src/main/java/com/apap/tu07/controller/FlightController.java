package com.apap.tu07.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.apap.tu07.model.FlightModel;
import com.apap.tu07.model.PilotModel;
import com.apap.tu07.service.FlightService;
import com.apap.tu07.service.PilotService;

/**
 * FlightController
 */

@RestController
@RequestMapping("/flight")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private PilotService pilotService;

    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
    private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
        PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber).get();
        pilot.setListFlight(new ArrayList<FlightModel>() {
            private ArrayList<FlightModel> init() {
                this.add(new FlightModel());
                return this;
            }
        }.init());

        model.addAttribute("pilot", pilot);
        return "add-flight";
    }


    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params = {"addRow"})
    private String addRow(@ModelAttribute PilotModel pilot, Model model) {
        pilot.getListFlight().add(new FlightModel());
        model.addAttribute("pilot", pilot);
        return "add-flight";
    }


    @RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params = {"removeRow"})
    public String removeRow(@ModelAttribute PilotModel pilot, Model model, HttpServletRequest req) {
        Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        pilot.getListFlight().remove(rowId.intValue());

        model.addAttribute("pilot", pilot);
        return "add-flight";
    }


    @PostMapping(value = "/add")
    public FlightModel addFlightSubmit(@RequestBody FlightModel flight) {
        return flightService.addFlight(flight);
    }


    @GetMapping(value = "/view/{flightNumber}")
    public FlightModel flightView(@PathVariable("flightNumber") String flightNumber) {

        FlightModel archive = flightService.getFlightDetailByFlightNumber(flightNumber).get();
        return archive;
    }

    @GetMapping(value = "/view/all")
    public List<FlightModel> flightView() {
        return flightService.getAllFlight();
    }

    @DeleteMapping(value = "/delete/{flightId}")
    public String deleteFlight(@PathVariable("flightId") long FlightId) {

        FlightModel flight = flightService.getFlightById(FlightId).get();
        flightService.deleteByFlightNumber(flight.getFlightNumber());
        return "flight has been deleted";
    }

    @RequestMapping(value = "/flight/update", method = RequestMethod.GET)
    private String update(@RequestParam(value = "flightNumber") String flightNumber, Model model) {

        FlightModel archive = flightService.getFlightDetailByFlightNumber(flightNumber).get();
        model.addAttribute("flight", archive);
        return "update-flight";
    }

    @PutMapping(value = "/update/{flightId}")
    public String updatePilotSubmit(@PathVariable("flightId") long flightId,
                                    @RequestParam(value = "destination") String destination,
                                    @RequestParam(value = "origin") String origin,
                                    @RequestParam(value = "time") Date time) {
        Optional<FlightModel> flightModelOptional = flightService.getFlightById(flightId);
        if (!flightModelOptional.isPresent()) {
            return "404 Flight Not Found.";
        }

        FlightModel flight = flightModelOptional.get();
        flight.setDestination(destination);
        flight.setOrigin(origin);
        flight.setTime(time);
        flightService.updateFlight(flightId, flight);

        return "flight update success";
    }

    @RequestMapping(value = "/flight/update", method = RequestMethod.POST)
    private @ResponseBody
    FlightModel updateFlightSubmit(@ModelAttribute FlightModel flight) {
        flightService.addFlight(flight);
        return flight;
    }
}