package com.uraneptus.sullysmod.common.entities.components.workstations;

import com.uraneptus.sullysmod.core.registry.SMWorkstationTypes;

import static com.uraneptus.sullysmod.core.registry.SMWorkstationTypes.EMPTY_WORKSTATION_TYPE;

public class Empty extends AbstractWorkstation<Empty>{
    public static final Empty UNIT = new Empty();
    private Empty() {}

    @Override
    public SMWorkstationTypes.WorkstationType<Empty> getWorkstationType() {
        return EMPTY_WORKSTATION_TYPE.get();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
