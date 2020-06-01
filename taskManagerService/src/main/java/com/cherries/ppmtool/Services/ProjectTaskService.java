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
        //PTs to be added to specific project, not null
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
}
