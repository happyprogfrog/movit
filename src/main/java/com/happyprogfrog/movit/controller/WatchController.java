package com.happyprogfrog.movit.controller;

import com.happyprogfrog.movit.dto.watch.request.WatchReqAddDto;
import com.happyprogfrog.movit.dto.watch.request.WatchReqUpdateDto;
import com.happyprogfrog.movit.dto.watch.response.WatchResPickedDto;
import com.happyprogfrog.movit.dto.watch.response.WatchResSimpleDto;
import com.happyprogfrog.movit.service.WatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/watchlist")
@RequiredArgsConstructor
public class WatchController {

    private final WatchService watchService;

    // 관람 영화 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<WatchResSimpleDto>> getAllWatchList(@PathVariable("userId") Long userId) {
        List<WatchResSimpleDto> watchList = watchService.getAllWatchListByUserId(userId)
                .stream()
                .map(WatchResSimpleDto::new)
                .toList();
        return new ResponseEntity<>(watchList, HttpStatus.OK);
    }

    // 관람 영화 추가
    @PostMapping
    public ResponseEntity<WatchResSimpleDto> addWatch(@Valid @RequestBody WatchReqAddDto reqAddDto) {
        WatchResSimpleDto resSimpleDto = new WatchResSimpleDto(watchService.addWatch(reqAddDto));
        return new ResponseEntity<>(resSimpleDto, HttpStatus.CREATED);
    }

    // 관람 영화 삭제
    @DeleteMapping("/{watchId}")
    public ResponseEntity<Void> deleteWatch(@PathVariable("watchId") Long watchId) {
        watchService.deleteWatch(watchId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 인생 영화 조회
    @GetMapping("/users/{userId}/pick")
    public ResponseEntity<List<WatchResPickedDto>> getPickedWatchList() {
        List<WatchResPickedDto> pickedWatchList = watchService.getPickedWatchList()
                .stream()
                .map(WatchResPickedDto::new)
                .toList();
        return new ResponseEntity<>(pickedWatchList, HttpStatus.OK);
    }

    // 인생 영화 추가
    @PatchMapping("/{watchId}/pick")
    public ResponseEntity<WatchResPickedDto> addPick(@PathVariable("watchId") Long watchId, @Valid @RequestBody WatchReqUpdateDto reqUpdateDto) {
        WatchResPickedDto resPickedDto = new WatchResPickedDto(watchService.changePickStatus(watchId, reqUpdateDto));
        return new ResponseEntity<>(resPickedDto, HttpStatus.OK);
    }
}
