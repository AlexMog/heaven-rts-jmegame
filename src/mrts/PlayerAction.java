/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mrts;

import mrts.units.Unit;
import java.util.LinkedList;
import java.util.List;
import util.Pair;

/**
 *
 * @author santi
 */
public class PlayerAction {
    List<Pair<Unit,UnitAction>> actions = new LinkedList<Pair<Unit,UnitAction>>();
    ResourceUsage r = new ResourceUsage();
    
    public PlayerAction() {
        
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof PlayerAction)) return false;
        PlayerAction a = (PlayerAction)o;

        for(Pair<Unit,UnitAction> p:actions) {
            for(Pair<Unit,UnitAction> p2:a.actions) {
                if (p.m_a.getID()==p2.m_a.getID() &&
                    !p.m_b.equals(p2.m_b)) return false;
            }
        }
        return true;
    }    
    
    
    public boolean isEmpty() {
        return actions.isEmpty();
    }

    public boolean hasNonNoneActions() {
        for(Pair<Unit,UnitAction> ua:actions) {
            if (ua.m_b.type!=UnitAction.TYPE_NONE) return true;
        }
        return false;
    }
    
    public ResourceUsage getResourceUsage() {
        return r;
    }
    
    public void setResourceUsage(ResourceUsage a_r) {
        r = a_r;
    }
    
    public void addUnitAction(Unit u, UnitAction a) {
        actions.add(new Pair<Unit,UnitAction>(u,a));
    }
    
    public PlayerAction merge(PlayerAction a) {
        PlayerAction merge = new PlayerAction();
        for(Pair<Unit,UnitAction> ua:actions) merge.actions.add(ua);
        for(Pair<Unit,UnitAction> ua:a.actions) merge.actions.add(ua);
        merge.r = r.mergeIntoNew(a.r);
        
        return merge;
    }
    
    public List<Pair<Unit,UnitAction>> getActions() {
        return actions;
    }
    
    public List<PlayerAction> cartesianProduct(List<UnitAction> lu, Unit u, GameState s) {
        List<PlayerAction> l = new LinkedList<PlayerAction>();
        
        for(UnitAction ua:lu) {
            ResourceUsage r2 = ua.resourceUsage(u, s.getPhysicalGameState());
            if (r.consistentWith(r2, s)) {
                PlayerAction a = new PlayerAction();
                a.r = r.mergeIntoNew(r2);
                a.actions.addAll(actions);
                a.addUnitAction(u, ua);
                l.add(a);                
            }
        }
        
        return l;
    }
    
    
    public boolean consistentEith(ResourceUsage u, GameState gs) {
        return r.consistentWith(u, gs);
    }
    
    
    public void fillWithNones(GameState s, int pID) {
        // assign "none" to all the units that need an action and do not have one:
        PhysicalGameState pgs = s.getPhysicalGameState();
        for(Unit u:pgs.getUnits()) {
            if (u.getPlayer() == pID) {
                if (s.unitActions.get(u)==null) {
                    boolean found = false;
                    for(Pair<Unit,UnitAction> pa:actions) {
                        if (pa.m_a==u) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        actions.add(new Pair<Unit,UnitAction>(u, new UnitAction(UnitAction.TYPE_NONE)));
                    }
                }
            }
        }
    }
    
    
    public String toString() {
        String tmp = "{ ";
        for(Pair<Unit,UnitAction> ua:actions) {
            tmp += "(" + ua.m_a + "," + ua.m_b + ")";
        }
        return tmp + " }";
    }
}
