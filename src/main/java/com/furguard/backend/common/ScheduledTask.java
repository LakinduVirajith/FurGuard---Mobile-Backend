package com.furguard.backend.common;

import com.furguard.backend.entity.LostPetNotice;
import com.furguard.backend.entity.PetProfile;
import com.furguard.backend.repository.LostPetNoticeRepository;
import com.furguard.backend.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledTask {

    private final LostPetNoticeRepository noticeRepository;
    private final ProfileRepository profileRepository;

    @Scheduled(cron = "0 0 0 * * *")  // Run every day at midnight
    public void removeFoundNoticesScheduledTask() {
        removeFoundNoticesOlderThan7Days();
        removeNotExistProfiles();
    }

    private void removeFoundNoticesOlderThan7Days() {
        LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);
        List<LostPetNotice> foundNoticesToRemove = noticeRepository.findByIsFoundAndFoundDateBefore(true, sevenDaysAgo);

        for (LostPetNotice notice : foundNoticesToRemove) {
            noticeRepository.deleteById(notice.getLostPetNoticeId());
        }
    }

    private void removeNotExistProfiles(){
        LocalDate sixMonthAgo = LocalDate.now().minusMonths(6);
        List<PetProfile> petProfiles = profileRepository.findByDeactivatedDateBefore(sixMonthAgo);

        if(!petProfiles.isEmpty()){
            for (PetProfile profile : petProfiles) {
                profile.setUser(null);
                profileRepository.delete(profile);
            }
        }
    }
}
