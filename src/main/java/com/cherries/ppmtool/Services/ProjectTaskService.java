package com.cherries.ppmtool.Services;

import com.cherries.ppmtool.domain.Backlog;
import com.cherries.ppmtool.domain.Project;
import com.cherries.ppmtool.domain.ProjectTask;
import com.cherries.ppmtool.exceptions.ProjectNotFoundException;
import com.cherries.ppmtool.repositories.BacklogRepository;
import com.cherries.ppmtool.repositories.ProjectRepository;
import com.cherries.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;
    @Autowired
    private ProjectRepository projectRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        //TODO refactor this
        try {
            Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            //set the bl to pt
            projectTask.setBacklog(backlog);
            //sequence
            backlog.setPTSequence(backlog.getPTSequence() + 1); ;

            //Add sedquence to project task
            projectTask.setProjectSequence(projectIdentifier + "-" + backlog.getPTSequence());
            projectTask.setProjectIdentifier(projectIdentifier);
            //priority
            if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
                projectTask.setPriority(3);
            }
            if (projectTask.getStatus() == null || projectTask.getStatus().equals("")) {
                projectTask.setStatus("TO_DO");
            }
            return projectTaskRepository.save(projectTask);
        } catch (Exception e) {
            throw new ProjectNotFoundException("Project ID '" + projectIdentifier.toUpperCase() + "' doesn't exists");
        }
    }

    public List<ProjectTask> findBacklogById(String id) {
        try{
            Project project  = projectRepository.findByProjectIdentifier(id);
            return projectTaskRepository.findByProjectIdentifierOrderByPriorityDesc(project.getProjectIdentifier());
        }catch (Exception e){
            throw new ProjectNotFoundException("Project ID '" + id.toUpperCase() + "' doesn't exists");
        }
    }

    public ProjectTask findPTProjectBySequence(String backlog_id, String pt_id){
        Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        if(backlog == null){
            throw new ProjectNotFoundException("Project ID '" + backlog_id.toUpperCase() + "' doesn't exists");
        }
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
        if(projectTask == null){
            throw new ProjectNotFoundException("Project Task '" + pt_id.toUpperCase() + "' not found");
        }
        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task '" + pt_id.toUpperCase() + "' does not exists in project: '" + backlog_id + "'" );
        }

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id){

        ProjectTask projectTask = findPTProjectBySequence(backlog_id, pt_id);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }
    public void deleteByProjectSequence(String backlog_id, String pt_id){
        ProjectTask projectTask = findPTProjectBySequence(backlog_id, pt_id);
        projectTaskRepository.delete(projectTask);
    }
}
