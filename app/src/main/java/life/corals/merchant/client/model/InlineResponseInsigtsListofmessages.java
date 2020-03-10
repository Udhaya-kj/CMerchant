package life.corals.merchant.client.model;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import io.swagger.v3.oas.annotations.media.Schema;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-01-10T08:23:33.721Z")
public class InlineResponseInsigtsListofmessages {
  @SerializedName("day")
  private String day = null;

  @SerializedName("value")
  private String value = null;

  public InlineResponseInsigtsListofmessages day(String day) {
    this.day = day;
    return this;
  }

   /**
   * Get day
   * @return day
  **/
   @Schema(description = "")
  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public InlineResponseInsigtsListofmessages value(String value) {
    this.value = value;
    return this;
  }

   /**
   * Get value
   * @return value
  **/
   @Schema(description = "")
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponseInsigtsListofmessages inlineResponseInsigtsListofmessages = (InlineResponseInsigtsListofmessages) o;
    return Objects.equals(this.day, inlineResponseInsigtsListofmessages.day) &&
        Objects.equals(this.value, inlineResponseInsigtsListofmessages.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(day, value);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponseInsigtsListofmessages {\n");
    
    sb.append("    day: ").append(toIndentedString(day)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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

