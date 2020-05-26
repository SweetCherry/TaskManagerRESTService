package com.cherries.ppmtool.Services;

import com.cherries.ppmtool.domain.Project;
import com.cherries.ppmtool.exceptions.ProjectIdException;
import com.cherries.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProjectService  {
    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
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
