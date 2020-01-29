/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicamp.meca.models;

import br.unicamp.cst.motivational.Appraisal;
import br.unicamp.cst.representation.owrl.AbstractObject;
import br.unicamp.meca.util.Encoder;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wander
 */
public class Episode {
//arrumar os nomes pra left right de novo
  private List<float[]> initialState;
  private List<float[]> terminalState;
  private Appraisal appraisal;
  private String actionTaken;
  private List<String> currentActions = new ArrayList<>();
  private String id;

  public Episode(){}
  
  public Episode(List<float[]> initialState, List<float[]> terminalState) {
    this.initialState = initialState;
    this.terminalState = terminalState;
    this.id = UUID.randomUUID().toString();
  }

  public Episode(AbstractObject initialStateOWRL, AbstractObject terminalStateOWRL, Encoder encoder) {
      this.initialState = encoder.encodeConfiguration(initialStateOWRL);
      this.terminalState = encoder.encodeConfiguration(terminalStateOWRL);
      this.id = UUID.randomUUID().toString();
  }

 
  public List<float[]> getInitialState() {
      return initialState;
  }

  public void setInitialState(List<float[]> initialState) {
      this.initialState = initialState;
  }

 
  public List<float[]> getTerminalState() {
      return this.terminalState;
  }
  
  public void setTerminalState(List<float[]> terminalState) {
      this.terminalState = terminalState;
  }

  public Appraisal getAppraisal() { 
      return appraisal; 
  }
  
  public void setAppraisal(Appraisal app) {
      this.appraisal = app; 
  }
  
  public String getActionTaken() { 
      return actionTaken; 
  }
  
  public void setActionTaken(String actionTaken) {
      this.actionTaken = actionTaken; 
  }
  
  public List<String> getCurrentActions() { 
      return currentActions; 
  }
  
  public void addCurrentActions(String actionTaken) {
      this.currentActions.add(actionTaken); 
  }
  
  public void removeCurrentActions(String actionTaken) {
      this.currentActions.remove(actionTaken); 
  }
  
  @Override
  public int hashCode() { return initialState.hashCode() ^ terminalState.hashCode(); }


    public boolean equals(List<float[]> cueObject, double similarity) {
      double diff = 0;
      int numberOfElements = 0;
      //add the difference between each element of cue and initialState
      for(int j =0; j< cueObject.size(); j++){
          float[] row = cueObject.get(j);
          for(int i =0; i< row.length; i++){
              diff +=Math.abs(row[i] - this.getInitialState().get(j)[i]);
              numberOfElements++;
          }
      }
        //if diference less than (1-x)%, that is, x% similarity, return true
        return (diff / ((double) numberOfElements)) < (1.0 - similarity);
    }

    public double diff(List<float[]> cueObject){
        double diff = 0;
        //add the difference between each element of cue and initialState
        for(int j =0; j< cueObject.size(); j++){
            float[] row = cueObject.get(j);
            for(int i =0; i< row.length; i++){
                diff +=Math.abs(row[i] - this.getInitialState().get(j)[i]);
            }
        }
        return diff;
    }

  public void setId(String id){
      this.id = id;
  }

  public String getId(){
      return this.id;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Episode)) return false;
    Episode pairo = (Episode) o;
    return this.initialState.equals(pairo.getInitialState()) &&
           this.terminalState.equals(pairo.getTerminalState());
  }

}
