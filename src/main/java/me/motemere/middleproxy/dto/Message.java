package me.motemere.middleproxy.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class Message {

  private Integer sessionId;
  private Long entryPointTimestamp;
  private Long middleProxyTimestamp;
  private Long endProxyTimestamp;
  private Long finalTimestamp;

  public Message(Integer idSession, Long ms1Timestamp, Long ms2Timestamp, Long ms3Timestamp,
      Long endTimestamp) {
    this.sessionId = idSession;
    this.entryPointTimestamp = ms1Timestamp;
    this.middleProxyTimestamp = ms2Timestamp;
    this.endProxyTimestamp = ms3Timestamp;
    this.finalTimestamp = endTimestamp;
  }

  /**
   * Override toString method.
   *
   * @return String representation of this object.
   */
  @Override
  public String toString() {
    return "{"
        + "sessionId=" + sessionId
        + ", entryPointTimestamp=" + entryPointTimestamp
        + ", middleProxyTimestamp=" + middleProxyTimestamp
        + ", endProxyTimestamp=" + endProxyTimestamp
        + ", finalTimestamp=" + finalTimestamp
        + '}';
  }

  /**
   * Make JSON string from this object.
   *
   * @return JSON string.
   */
  public String toJson() {
    return "{"
        + "\"sessionId\":" + sessionId
        + ",\"entryPointTimestamp\":" + entryPointTimestamp
        + ",\"middleProxyTimestamp\":" + middleProxyTimestamp
        + ",\"endProxyTimestamp\":" + endProxyTimestamp
        + ",\"finalTimestamp\":" + finalTimestamp
        + '}';
  }
}
