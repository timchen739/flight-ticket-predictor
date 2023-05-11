package entity;

public class ModelInput {
    private String route;
    private String searchDate;
    private String fromDate;
    private String toDate;
    public static class Builder {
        private String route;
        private String searchDate;
        private String fromDate;
        private String toDate;

        public Builder route(String route) {
            this.route = route;
            return this;
        }

        public Builder searchDate(String searchDate) {
            this.searchDate = searchDate;
            return this;
        }

        public Builder fromDate(String fromDate) {
            this.fromDate = fromDate;
            return this;
        }

        public Builder toDate(String toDate) {
            this.toDate = toDate;
            return this;
        }

        public ModelInput defaultInputOne() {
            this.route = "BOX-DXB";
            this.searchDate = "05/08/2023";
            this.fromDate = "06/03/2023";
            this.toDate = "06/09/2023";
            return new ModelInput(this);
        }

        public ModelInput defaultInputTwo() {
            this.route = "BOX-DXB";
            this.searchDate = "05/09/2023";
            this.fromDate = "06/03/2023";
            this.toDate = "06/09/2023";
            return new ModelInput(this);
        }

        public ModelInput build() {
            return new ModelInput(this);
        }
    }

    private ModelInput(Builder builder) {
        this.route = builder.route;
        this.searchDate = builder.searchDate;
        this.fromDate = builder.fromDate;
        this.toDate = builder.toDate;
    }

    public String getSearchDate() {
        return this.searchDate;
    }
}
