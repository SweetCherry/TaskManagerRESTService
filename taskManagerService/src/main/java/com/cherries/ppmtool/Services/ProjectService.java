package com.cherries.ppmtool.Services;

import com.cherries.ppmtool.domain.Project;
import com.cherries.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService  {
    @Autowired
    private ProjectRepository projectRepository;

    public Project savOrUpdateProject(Project project){
        //TODO logic
        return projectRepository.save(project);
    }

}
