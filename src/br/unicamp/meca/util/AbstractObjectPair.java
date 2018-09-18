/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicamp.meca.util;

import br.unicamp.cst.motivational.Appraisal;
import br.unicamp.cst.representation.owrl.AbstractObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wander
 */
public class AbstractObjectPair {
//arrumar os nomes pra left right de novo
  private AbstractObject before;
  private AbstractObject after;
  private Appraisal appraisal;
  private String actionTaken;
  private List<String> currentActions = new ArrayList<>();

  public AbstractObjectPair(){}
  
  public AbstractObjectPair(AbstractObject left, AbstractObject right) {
    this.before = left;
    this.after = right;
  }
 
  public AbstractObject getBefore() { 
      return before; 
  }
  
  public void setBefore(AbstractObject bef) { 
      this.before = bef; 
  }
  
 
  public AbstractObject getAfter() { 
      return after; 
  }
  
  public void setAfter(AbstractObject aft) {
      this.after = aft; 
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
  public int hashCode() { return before.hashCode() ^ after.hashCode(); }

  //sempre vai dar falso essa bodega. arrumar
  
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof AbstractObjectPair)) return false;
    AbstractObjectPair pairo = (AbstractObjectPair) o;
    return this.before.equals(pairo.getBefore()) &&
           this.after.equals(pairo.getAfter());
  }

}
