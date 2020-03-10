package life.corals.merchant.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2019-09-19T07:17:58.745Z[GMT]")
public class InlineResponse2002 {
  @SerializedName("success_code")
  private String successCode = null;

  @SerializedName("success_message")
  private String successMessage = null;

  @SerializedName("cust_id")
  private String custId = null;

  public InlineResponse2002 successCode(String successCode) {
    this.successCode = successCode;
    return this;
  }

  @Schema(description = "")
  public String getSuccessCode() {
    return successCode;
  }

  public void setSuccessCode(String successCode) {
    this.successCode = successCode;
  }

  public InlineResponse2002 successMessage(String successMessage) {
    this.successMessage = successMessage;
    return this;
  }

  @Schema(description = "")
  public String getSuccessMessage() {
    return successMessage;
  }

  public void setSuccessMessage(String successMessage) {
    this.successMessage = successMessage;
  }

  public InlineResponse2002 custId(String custId) {
    this.custId = custId;
    return this;
  }

  @Schema(description = "")
  public String getCustId() {
    return custId;
  }

  public void setCustId(String custId) {
    this.custId = custId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse2002 inlineResponse2002 = (InlineResponse2002) o;
    return Objects.equals(this.successCode, inlineResponse2002.successCode) &&
        Objects.equals(this.successMessage, inlineResponse2002.successMessage) &&
        Objects.equals(this.custId, inlineResponse2002.custId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(successCode, successMessage, custId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2002 {\n");
    sb.append("    successCode: ").append(toIndentedString(successCode)).append("\n");
    sb.append("    successMessage: ").append(toIndentedString(successMessage)).append("\n");
    sb.append("    custId: ").append(toIndentedString(custId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
