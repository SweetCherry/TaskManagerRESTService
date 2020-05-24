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

    public Project savOrUpdateProject(Project project){
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
        }

    }
    public Project findProjectByIdentifier(String projectId){
        return projectRepository.findByProjectIdentifier(projectId.toUpperCase());
    }

}
