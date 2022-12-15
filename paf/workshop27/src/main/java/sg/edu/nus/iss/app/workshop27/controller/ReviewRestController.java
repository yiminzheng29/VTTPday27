package sg.edu.nus.iss.app.workshop27.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.edu.nus.iss.app.workshop27.models.EditedComment;
import sg.edu.nus.iss.app.workshop27.models.Review;
import sg.edu.nus.iss.app.workshop27.service.ReviewService;

@RestController
@RequestMapping(path = "/api/review")
public class ReviewRestController {
    @Autowired
    private ReviewService reviewSvc;


    // PUT method in POSTMAN: update comments in raw --> Json
    // localhost:8080/api/review/639a954b152c335a9ca9fab6
    @PutMapping(path="{_id}")
    public ResponseEntity<String> updateEdited(@PathVariable String _id, @RequestBody EditedComment e) {
        JsonObject result = null;
        Review upsertRv = reviewSvc.updateEdits(_id, e);
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("review", upsertRv.toJSON(false));
        result = builder.build();

        return ResponseEntity
        .status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(result.toString());

    }

    // GET method in POSTMAN: localhost:8080/api/review/639a954b152c335a9ca9fab6
    @GetMapping("{reviewId}")
    public ResponseEntity<String> getReviewById(@PathVariable String reviewId) {
        JsonObject result = null;
        Review r = reviewSvc.getReview(reviewId);
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("review", r.toJSON(true));
        result = builder.build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    @GetMapping("{reviewId}/history")
    public ResponseEntity<String> getReviewHistory(@PathVariable String reviewId) {
        JsonObject result = null;
        Review r = reviewSvc.getReview(reviewId);
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("review", r.toJSON(false));
        result = builder.build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

}
