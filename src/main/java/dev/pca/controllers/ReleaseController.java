package dev.pca.controllers;

import dev.pca.controllers.exceptions.ReleaseNotFoundException;
import dev.pca.models.Release;
import dev.pca.services.ReleaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

@RestController
@RequestMapping(path="v0/fab/releases", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReleaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReleaseController.class);

    private final ReleaseService releaseService;

    @Autowired
    public ReleaseController(ReleaseService releaseService) {
        checkArgument(releaseService != null, "releaseService cannot be null.");
        this.releaseService = releaseService;
    }

    @PostMapping
    public ResponseEntity<Collection<Release>> createRelease(@RequestBody List<Release> releases) {
        LOGGER.debug("operation='createRelease', message='{}'", releases);
        return new ResponseEntity<>(releaseService.insert(releases), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<Release>> getAllReleases() {
        LOGGER.debug("getAllReleases='getAllReleases', message=''");
        return buildCollectionResponse(releaseService.getAll(), "Empty DB");
    }

    @GetMapping(params = "ids")
    public ResponseEntity<Collection<Release>> getByReleaseId(@RequestParam List<String> ids) {
        LOGGER.debug("operation='getByReleaseId', message='{}'", ids);
        Optional<Collection<Release>> releases = releaseService.getByReleaseId(ids);
        return buildCollectionResponse(releases, String.format("Release ID: %s not found.", ids));
    }

    @GetMapping(params = "codes")
    public ResponseEntity<Collection<Release>> getByReleaseCode(@RequestParam List<String> codes) {
        LOGGER.debug("operation='getByReleaseCode', message='{}'", codes);
        Optional<Collection<Release>> releases = releaseService.getByReleaseCode(codes);
        return buildCollectionResponse(releases, String.format("Release code: %s not found.", codes));
    }

    @GetMapping(path = "search")
    public ResponseEntity<Collection<Release>> getByReleaseFilter(@RequestParam Map<String, String> queryParams) {
        LOGGER.debug("operation='getByReleaseFilter', message='{}'", queryParams);
        Optional<Collection<Release>> releases = releaseService.filterBy(queryParams);
        return buildCollectionResponse(releases, String.format("No releases matches the search parameters."));
    }

    private ResponseEntity<Collection<Release>> buildCollectionResponse(Optional<Collection<Release>> releases, String errorMessage) {
        if (releases.isEmpty()) {
            throw new ReleaseNotFoundException(errorMessage);
        }
        return ResponseEntity.ok(releases.get());
    }
}
