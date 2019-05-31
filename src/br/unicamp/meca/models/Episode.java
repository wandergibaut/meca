/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicamp.meca.models;

import br.unicamp.cst.motivational.Appraisal;
import br.unicamp.cst.representation.owrl.AbstractObject;
import br.unicamp.meca.util.Encoder;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wander
 */
public class Episode {
//arrumar os nomes pra left right de novo
  private INDArray initialState;
  private INDArray terminalState;
  private Appraisal appraisal;
  private String actionTaken;
  private List<String> currentActions = new ArrayList<>();

  public Episode(){}
  
  public Episode(INDArray initialState, INDArray terminalState) {
    this.initialState = initialState;
    this.terminalState = terminalState;
  }

  public Episode(AbstractObject initialStateOWRL, AbstractObject terminalStateOWRL, Encoder encoder) {
      this.initialState = encoder.encodeConfiguration(initialStateOWRL);
      this.terminalState = encoder.encodeConfiguration(terminalStateOWRL);
  }

 
  public INDArray getInitialState() {
      return initialState;
  }
  
  public void setInitialState(INDArray initialState) {
      this.initialState = initialState;
  }
  
 
  public INDArray getTerminalState() {
      return this.terminalState;
  }
  
  public void setTerminalState(INDArray terminalState) {
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

  
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Episode)) return false;
    Episode pairo = (Episode) o;
    return this.initialState.equals(pairo.getInitialState()) &&
           this.terminalState.equals(pairo.getTerminalState());
  }

}
