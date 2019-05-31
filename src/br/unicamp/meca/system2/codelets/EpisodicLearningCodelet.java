/*******************************************************************************
 * Copyright (c) 2018  DCA-FEEC-UNICAMP                  *
 * All rights reserved. This program and the accompanying materials            *
 * are made available under the terms of the GNU Lesser Public License v3      *
 * which accompanies this distribution, and is available at                    *
 * http://www.gnu.org/licenses/lgpl.html                                       *
 *                                                                             *
 * Contributors:                                                               *
 *     W. Gibaut and R. R. Gudwin                                              * 
 *                                                                             *
 *                                                                             *
 ******************************************************************************/
package br.unicamp.meca.system2.codelets;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.representation.owrl.AbstractObject;
import br.unicamp.cst.representation.owrl.Property;
import br.unicamp.cst.representation.owrl.QualityDimension;
import br.unicamp.meca.util.Episode;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author W. Gibaut
 */
public abstract class EpisodicLearningCodelet extends Codelet{
    
    protected String id;
    
    protected String episodicAttentionCodeletId;
    protected String episodicRetrievalCodeletId;
    
    protected Memory episodicMemory;
    protected Memory episodicBufferMemory;
    
    private List<Memory> episodicMemoryMOList;
    private List<Episode> episodicBufferList;
    //private Memory syncWait;


    
    
    public EpisodicLearningCodelet(String id, String episodicAttentionCodeletId, String episodicRetrievalCodeletId){
        this.id = id;
        this.episodicAttentionCodeletId = episodicAttentionCodeletId;
        this.episodicRetrievalCodeletId = episodicRetrievalCodeletId;
	setName(id);
        
        episodicMemoryMOList = new ArrayList<>();
    }
    
    @Override
    public void accessMemoryObjects() {
        if(episodicBufferMemory==null && episodicAttentionCodeletId!=null)
			this.episodicBufferMemory = this.getInput(episodicAttentionCodeletId);
                
                if(episodicMemory==null)
			this.episodicMemory = this.getOutput(this.id);

    }

    @Override
    public void proc() {
        
        addEncondingEp();
        //set nas memorias

    }
    
    public abstract void addEncondingEp();


    public boolean equals (Episode first, Episode second){
        boolean result = false;
        
        if(first.getTerminalState().equals(second.getTerminalState()) ||
                first.getInitialState().equals(second.getInitialState())){
            result = true;
        }
        
        return result;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public String getId(){
        return this.id;
    }
    
    public boolean containsName(final List<AbstractObject> list, final String name){
        return list.stream().filter(o -> o.getName().equals(name)).findFirst().isPresent();
    }
    
    public AbstractObject getAbstractObjectStruct(final AbstractObject list, final String name){
        return list.getCompositeParts().stream().filter(o -> o.getName().equals(name)).findFirst().get();
    }
    
    public boolean containsProperty(final AbstractObject list, final String name){
        return list.getProperties().stream().filter(o -> o.getName().equals(name)).findFirst().isPresent();
    }
    
    public Property getPropertyStruct(final AbstractObject list, final String name){
        return list.getProperties().stream().filter(o -> o.getName().equals(name)).findFirst().get();
    }
    
    public QualityDimension getQDStruct(final Property list, final String name){
        return list.getQualityDimensions().stream().filter(o -> o.getName().equals(name)).findFirst().get();
    }
    
}