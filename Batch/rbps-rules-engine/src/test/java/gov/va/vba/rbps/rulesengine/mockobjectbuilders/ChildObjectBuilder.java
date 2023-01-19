package gov.va.vba.rbps.rulesengine.mockobjectbuilders;

import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.ChildType;
import gov.va.vba.rbps.coreframework.xom.Veteran;

public class ChildObjectBuilder {
    public static Child createChild(ChildType childType, Veteran veteran) {
        Child child = new Child();

        child.setChildType(childType);
        child.setLivingWith(veteran);

        return child;
    }
}
