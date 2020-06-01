package com.cherries.ppmtool.Services;

import com.cherries.ppmtool.domain.Backlog;
import com.cherries.ppmtool.domain.Project;
import com.cherries.ppmtool.exceptions.ProjectIdException;
import com.cherries.ppmtool.repositories.BacklogRepository;
import com.cherries.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProjectService  {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project){
        try {
            project.setProjectIdentifier( project.getProjectIdentifier().toUpperCase());
            this.setBacklogToProject(project);
            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
        }

    }
    private void setBacklogToProject(Project project){
        String projectIdentifierUpperCase = project.getProjectIdentifier().toUpperCase();
        if(project.getId()==null){
            Backlog backlog = new Backlog();
            project.setBacklog(backlog);
            backlog.setProject(project);
            backlog.setProjectIdentifier(projectIdentifierUpperCase);
        }else{
            project.setBacklog(backlogRepository.findByProjectIdentifier(projectIdentifierUpperCase));
        }
    }

    public Project findProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Project ID '" + projectId.toUpperCase() + "' does not exits");
        }
        return project;
    }
    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String identifier){
        Project project = projectRepository.findByProjectIdentifier(identifier.toUpperCase());
        if(project == null){
            throw new ProjectIdException("Cannot delete project with ID '" + identifier.toUpperCase() + "'. This project does not exists");
        }
        projectRepository.delete(project);


    }

}
