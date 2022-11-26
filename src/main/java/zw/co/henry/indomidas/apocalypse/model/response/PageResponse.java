package zw.co.henry.indomidas.apocalypse.model.response;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

//@Data //for getters and setters
public class PageResponse extends OperationResponse
{
   @Getter
   @Setter
   private boolean first;
   @Getter
   @Setter
   private boolean last;
   @Getter
   @Setter
   private int currentPageNumber;
   @Getter
   @Setter
   private int itemsInPage;
   @Getter
   @Setter
   private int pageSize;
   @Getter
   @Setter
   private int totalPages;
   @Getter
   @Setter
   private long totalItems;
   @Getter
   @Setter
   private Sort sort;
   private List items;

   public void setPageStats(Page page, boolean setDefaultMessage)
   {
      this.first = page.isFirst();
      this.last = page.isLast();
      this.currentPageNumber = page.getNumber();
      this.itemsInPage = page.getNumberOfElements();
      this.pageSize = page.getSize();
      this.totalPages = page.getTotalPages();
      this.totalItems = page.getTotalElements();
      //this.items             = page.getContent();
      this.sort = page.getSort();
      if (setDefaultMessage) {
         this.setOperationStatus(ResponseStatusEnum.SUCCESS);
         this.setOperationMessage("Page " + (page.getNumber() + 1) + " of " + page.getTotalPages());
      }
   }

   public void setPageTotal(int count, boolean setDefaultMessage)
   {
      //this.items             = list;
      this.first = true;
      this.last = true;
      this.itemsInPage = count;
      this.totalItems = count;
      this.totalPages = 1;
      this.pageSize = count;
      if (setDefaultMessage) {
         this.setOperationStatus(ResponseStatusEnum.SUCCESS);
         this.setOperationMessage("Total " + count + " items ");
      }
   }

}
