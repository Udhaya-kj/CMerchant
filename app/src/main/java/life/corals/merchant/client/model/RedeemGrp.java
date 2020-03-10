package life.corals.merchant.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * RedeemGrp
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-02-28T11:06:04.949Z")
public class RedeemGrp {
  @SerializedName("groupid")
  private String groupid = null;

  @SerializedName("groupname")
  private String groupname = null;

  public RedeemGrp groupid(String groupid) {
    this.groupid = groupid;
    return this;
  }

   /**
   * Get groupid
   * @return groupid
  **/
  @Schema(description = "")
  public String getGroupid() {
    return groupid;
  }

  public void setGroupid(String groupid) {
    this.groupid = groupid;
  }

  public RedeemGrp groupname(String groupname) {
    this.groupname = groupname;
    return this;
  }

   /**
   * Get groupname
   * @return groupname
  **/
   @Schema(description = "")
  public String getGroupname() {
    return groupname;
  }

  public void setGroupname(String groupname) {
    this.groupname = groupname;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RedeemGrp redeemGrp = (RedeemGrp) o;
    return Objects.equals(this.groupid, redeemGrp.groupid) &&
        Objects.equals(this.groupname, redeemGrp.groupname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupid, groupname);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RedeemGrp {\n");
    
    sb.append("    groupid: ").append(toIndentedString(groupid)).append("\n");
    sb.append("    groupname: ").append(toIndentedString(groupname)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

