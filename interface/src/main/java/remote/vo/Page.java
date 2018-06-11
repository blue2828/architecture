package remote.vo;

public class Page implements java.io.Serializable{
    private int page;
    private int limit;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStart() {
        return (page - 1) * limit;
    }

    public Page(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }
}
