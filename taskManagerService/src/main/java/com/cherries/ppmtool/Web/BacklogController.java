package com.cherries.ppmtool.Web;

import com.cherries.ppmtool.Services.MapValidationError;
import com.cherries.ppmtool.Services.ProjectTaskService;

import com.cherries.ppmtool.domain.Project;
import com.cherries.ppmtool.domain.ProjectTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {
    @Autowired
    ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationError mapValidationError;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id){
        ResponseEntity<?> mapError = mapValidationError.MapValidationService(result);
        if(mapError != null)return mapError;

        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask);

        return new ResponseEntity<>(projectTask1, HttpStatus.CREATED);
    }
    @GetMapping("/{backlog_id}")
    public ResponseEntity<List<ProjectTask>> getProjectBacklog(@PathVariable String backlog_id){
        //happy path
        return new ResponseEntity<List<ProjectTask>>(projectTaskService.findBacklogById(backlog_id),HttpStatus.OK);
    }
    @GetMapping("/{backlog_id}/{pt_id")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id){
        ProjectTask projectTask = projectTaskService.findPTProjectBySequence(backlog_id ,pt_id);
        return new ResponseEntity<>(projectTask,HttpStatus.OK);
    }
    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id,
                                               @Valid @RequestBody ProjectTask projectTask, BindingResult result){
        //TODO Extracting error mapping
        ResponseEntity<?> errorMap = mapValidationError.MapValidationService(result);
        if(errorMap!=null)
            return errorMap;
        ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectTask, backlog_id, pt_id);
        return new ResponseEntity<>(updatedTask,HttpStatus.OK);

    }
}
