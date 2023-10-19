package school.sptech.ensine.util;

import school.sptech.ensine.domain.Aula;
import school.sptech.ensine.domain.Professor;

public class NodeAula {
    private Aula info;
    private NodeAula next;

    public NodeAula(Aula info) {
        this.info = info;
        this.next = null;
    }

    public Aula getInfo() {
        return info;
    }

    public void setInfo(Aula info) {
        this.info = info;
    }

    public NodeAula getNext() {
        return next;
    }

    public void setNext(NodeAula next) {
        this.next = next;
    }

}
