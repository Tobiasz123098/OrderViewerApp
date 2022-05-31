package com.example.domartorders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum StatusEnum {
    ALL("wszystkie", new String[]{"WITHDRAWN", "PLACED", "DURING_THE_WITHDRAWAL", "MODIFIED", "IN_PROGRESS", "SENT", "READY", "IN_STOCK", "PARTIALLY_SENT"}),
    COMPLETED("zakończone", new String[]{"WITHDRAWN"}),
    IN_PROCESS("w trakcie realizacji", new String[]{"IN_PROGRESS", "MODIFIED"}),
    OUT_FOR_DELIVERY("wyjechało", new String[]{"SENT"}),
    READY("gotowe", new String[]{"READY", "IN_STOCK"}),
    PARTIALLY_SHIPPED("częściowo wyjechało", new String[]{"PARTIALLY_SENT"});

    private final String name;
    private final String[] values;

    StatusEnum(String name, String[] values) {
        this.name = name;
        this.values = values;
    }

    public static List<String> getRelatedStatuses(String value) {

        Optional<StatusEnum> enumOptional = Arrays.stream(StatusEnum.values()).filter(val -> val.getName().equals(value)).findAny();
        return enumOptional.isPresent() ? Arrays.asList(enumOptional.get().getValues()) : new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String[] getValues() {
        return values;
    }

}
