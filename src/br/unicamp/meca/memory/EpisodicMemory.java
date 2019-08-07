/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicamp.meca.memory;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.MemoryObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author W. Gibaut
 */
public class EpisodicMemory {
    
    private List<Memory> episodes;
    private Memory episodesList = new MemoryObject();
    
    //constructor
    public EpisodicMemory(){
        episodes = new ArrayList<>();
        
    }
    
    //Methods
    public List<Memory> getEpisodes(){
        return episodes;
    }
    
    public Memory getEpisodesList(){
        
        return episodesList;
    }
    
    public void setEpisodes(List<Memory> eps){
        this.episodes = eps;
        this.episodesList.setI(eps);
    }
    
    public void setEpisodesList(List<Memory> eps){
        this.episodesList.setI(eps);
    }
    
    public void setEpisodesList(Memory epsList){
        this.episodesList = epsList;
    }
    
}
