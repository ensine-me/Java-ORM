package school.sptech.ensine.service.usuario.dto;

import school.sptech.ensine.enumeration.Status;

public class ContagemAulaStatus {

    private Status status;
    private Long total;

    public ContagemAulaStatus(Status status, Long total) {
        this.status = status;
        this.total = total;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
