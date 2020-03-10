package life.corals.merchant.client.model;

import java.util.Objects;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-01-07T09:51:59.026Z")
public class InlineResponseInsigts {
  @SerializedName("oldvalue")
  private String oldvalue = null;

  @SerializedName("newvalue")
  private String newvalue = null;

  @SerializedName("oldcustomer")
  private String oldcustomer = null;

  @SerializedName("newcustomer")
  private String newcustomer = null;

  @SerializedName("list_old")
  private List<InlineResponseInsigtsListofmessages> listOld = null;

  @SerializedName("list_new")
  private List<InlineResponseInsigtsListofmessages> listNew = null;

  public InlineResponseInsigts oldvalue(String oldvalue) {
    this.oldvalue = oldvalue;
    return this;
  }

  /**
   * Get oldvalue
   * @return oldvalue
   **/
  @Schema(description = "")
  public String getOldvalue() {
    return oldvalue;
  }

  public void setOldvalue(String oldvalue) {
    this.oldvalue = oldvalue;
  }

  public InlineResponseInsigts newvalue(String newvalue) {
    this.newvalue = newvalue;
    return this;
  }

  /**
   * Get newvalue
   * @return newvalue
   **/
  @Schema(description = "")
  public String getNewvalue() {
    return newvalue;
  }

  public void setNewvalue(String newvalue) {
    this.newvalue = newvalue;
  }

  public InlineResponseInsigts oldcustomer(String oldcustomer) {
    this.oldcustomer = oldcustomer;
    return this;
  }

  /**
   * Get oldcustomer
   * @return oldcustomer
   **/
  @Schema(description = "")
  public String getOldcustomer() {
    return oldcustomer;
  }

  public void setOldcustomer(String oldcustomer) {
    this.oldcustomer = oldcustomer;
  }

  public InlineResponseInsigts newcustomer(String newcustomer) {
    this.newcustomer = newcustomer;
    return this;
  }

  /**
   * Get newcustomer
   * @return newcustomer
   **/
  @Schema(description = "")
  public String getNewcustomer() {
    return newcustomer;
  }

  public void setNewcustomer(String newcustomer) {
    this.newcustomer = newcustomer;
  }

  public InlineResponseInsigts listOld(List<InlineResponseInsigtsListofmessages> listOld) {
    this.listOld = listOld;
    return this;
  }

  public InlineResponseInsigts addListOldItem(InlineResponseInsigtsListofmessages listOldItem) {
    if (this.listOld == null) {
      this.listOld = new ArrayList<InlineResponseInsigtsListofmessages>();
    }
    this.listOld.add(listOldItem);
    return this;
  }

  /**
   * Get listOld
   * @return listOld
   **/
  @Schema(description = "")
  public List<InlineResponseInsigtsListofmessages> getListOld() {
    return listOld;
  }

  public void setListOld(List<InlineResponseInsigtsListofmessages> listOld) {
    this.listOld = listOld;
  }

  public InlineResponseInsigts listNew(List<InlineResponseInsigtsListofmessages> listNew) {
    this.listNew = listNew;
    return this;
  }

  public InlineResponseInsigts addListNewItem(InlineResponseInsigtsListofmessages listNewItem) {
    if (this.listNew == null) {
      this.listNew = new ArrayList<InlineResponseInsigtsListofmessages>();
    }
    this.listNew.add(listNewItem);
    return this;
  }

  /**
   * Get listNew
   * @return listNew
   **/
  @Schema(description = "")
  public List<InlineResponseInsigtsListofmessages> getListNew() {
    return listNew;
  }

  public void setListNew(List<InlineResponseInsigtsListofmessages> listNew) {
    this.listNew = listNew;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponseInsigts inlineResponseInsigts = (InlineResponseInsigts) o;
    return Objects.equals(this.oldvalue, inlineResponseInsigts.oldvalue) &&
            Objects.equals(this.newvalue, inlineResponseInsigts.newvalue) &&
            Objects.equals(this.oldcustomer, inlineResponseInsigts.oldcustomer) &&
            Objects.equals(this.newcustomer, inlineResponseInsigts.newcustomer) &&
            Objects.equals(this.listOld, inlineResponseInsigts.listOld) &&
            Objects.equals(this.listNew, inlineResponseInsigts.listNew);
  }

  @Override
  public int hashCode() {
    return Objects.hash(oldvalue, newvalue, oldcustomer, newcustomer, listOld, listNew);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponseInsigts {\n");

    sb.append("    oldvalue: ").append(toIndentedString(oldvalue)).append("\n");
    sb.append("    newvalue: ").append(toIndentedString(newvalue)).append("\n");
    sb.append("    oldcustomer: ").append(toIndentedString(oldcustomer)).append("\n");
    sb.append("    newcustomer: ").append(toIndentedString(newcustomer)).append("\n");
    sb.append("    listOld: ").append(toIndentedString(listOld)).append("\n");
    sb.append("    listNew: ").append(toIndentedString(listNew)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
