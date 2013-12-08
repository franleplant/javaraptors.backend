package org.jr.be.dto;

import java.util.List;

public class AuthorSearchDTO {
        
        private int page_total;
        private int page_number;
        private List<AuthorDTO> results;
        
        public int getPage_total() {
                return page_total;
        }
        public void setPage_total(int page_total) {
                this.page_total = page_total;
        }
        public int getPage_number() {
                return page_number;
        }
        public void setPage_number(int page_number) {
                this.page_number = page_number;
        }
        public List<AuthorDTO> getResults() {
                return results;
        }
        public void setResults(List<AuthorDTO> results) {
                this.results = results;
        }

}