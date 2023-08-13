package com.furguard.backend.controller;

import com.furguard.backend.dto.LostPetNoticeDTO;
import com.furguard.backend.dto.LostPetNoticeDetailsDTO;
import com.furguard.backend.dto.RemindersDTO;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.service.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("home")
@AllArgsConstructor
@Tag(name = "Home Controller")
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/lost/notices")
    @Operation(
            summary = "Get All Lost Pet Notices",
            description = "Retrieve a list of all lost pet notices."
    )
    public List<LostPetNoticeDTO> getAllNotices() throws NotFoundException {
        return homeService.getAllNotices();
    }

    @GetMapping("/lost/notice/{id}")
    @Operation(
            summary = "Get Lost Pet Notice by ID",
            description = "Retrieve details of a lost pet notice by its ID."
    )
    public LostPetNoticeDetailsDTO getNoticeById(@PathVariable("id") Long noticeId) throws NotFoundException {
        return homeService.getNoticeById(noticeId);
    }

    @GetMapping("/reminders")
    @Operation(
            summary = "Get All Reminders",
            description = "Retrieve all medication and vaccination reminders for the pet."
    )
    public RemindersDTO getAllReminders() throws NotFoundException {
        return homeService.getAllReminders();
    }
}
