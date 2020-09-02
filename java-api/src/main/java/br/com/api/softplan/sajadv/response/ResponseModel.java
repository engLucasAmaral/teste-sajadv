package br.com.api.softplan.sajadv.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author lucas
 * @param <T>
 */
public class ResponseModel<T> {

    private List<T> data;
    private StatusResponse status;
    private String version;
    private String link_create;
    private List<Message> messages;

    public ResponseModel() {
        this.data = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public ResponseModel(List<T> data) {
        this.data = data;
    }

    public StatusResponse getStatus() {
        return status;
    }

    public void setStatus(StatusResponse status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the data
     */
    public List<T> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * @return the link_create
     */
    public String getLink_create() {
        return link_create;
    }

    /**
     * @param link_create the link_create to set
     */
    public void setLink_create(String link_create) {
        this.link_create = link_create;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.data);
        hash = 11 * hash + Objects.hashCode(this.status);
        hash = 11 * hash + Objects.hashCode(this.version);
        hash = 11 * hash + Objects.hashCode(this.link_create);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ResponseModel<?> other = (ResponseModel<?>) obj;
        return true;
    }

}
