package net.shchastnyi.serializer.messages;

import java.util.ArrayList;
import java.util.List;

public class PersonCyclic {
    public List<PersonCyclic> children = new ArrayList<>();
}
