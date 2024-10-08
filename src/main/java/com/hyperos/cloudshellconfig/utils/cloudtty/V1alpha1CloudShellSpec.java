/*
 * Kubernetes
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v1.21.1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.hyperos.cloudshellconfig.utils.cloudtty;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Spec defines the desired behavior of the cloudshell.
 */
@ApiModel(description = "Spec defines the desired behavior of the cloudshell.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-09-23T07:59:07.811Z[Etc/UTC]")
public class V1alpha1CloudShellSpec {
  public static final String SERIALIZED_NAME_CLEANUP = "cleanup";
  @SerializedName(SERIALIZED_NAME_CLEANUP)
  private Boolean cleanup;

  public static final String SERIALIZED_NAME_COMMAND_ACTION = "commandAction";
  @SerializedName(SERIALIZED_NAME_COMMAND_ACTION)
  private String commandAction;

  public static final String SERIALIZED_NAME_ENV = "env";
  @SerializedName(SERIALIZED_NAME_ENV)
  private List<V1alpha1CloudShellSpecEnv> env = null;

  /**
   * ExposeMode describes how to access ttyd service, either ClusterIP, NodePort, Ingress or VirtualService.
   */
  @JsonAdapter(ExposureModeEnum.Adapter.class)
  public enum ExposureModeEnum {
    CLUSTERIP("ClusterIP"),
    
    NODEPORT("NodePort"),
    
    INGRESS("Ingress"),
    
    VIRTUALSERVICE("VirtualService");

    private String value;

    ExposureModeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ExposureModeEnum fromValue(String value) {
      for (ExposureModeEnum b : ExposureModeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<ExposureModeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ExposureModeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ExposureModeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return ExposureModeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_EXPOSURE_MODE = "exposureMode";
  @SerializedName(SERIALIZED_NAME_EXPOSURE_MODE)
  private ExposureModeEnum exposureMode;

  public static final String SERIALIZED_NAME_IMAGE = "image";
  @SerializedName(SERIALIZED_NAME_IMAGE)
  private String image;

  public static final String SERIALIZED_NAME_INGRESS_CONFIG = "ingressConfig";
  @SerializedName(SERIALIZED_NAME_INGRESS_CONFIG)
  private V1alpha1CloudShellSpecIngressConfig ingressConfig;

  public static final String SERIALIZED_NAME_ONCE = "once";
  @SerializedName(SERIALIZED_NAME_ONCE)
  private Boolean once;

  public static final String SERIALIZED_NAME_PATH_PREFIX = "pathPrefix";
  @SerializedName(SERIALIZED_NAME_PATH_PREFIX)
  private String pathPrefix;

  public static final String SERIALIZED_NAME_PATH_SUFFIX = "pathSuffix";
  @SerializedName(SERIALIZED_NAME_PATH_SUFFIX)
  private String pathSuffix;

  public static final String SERIALIZED_NAME_SECRET_REF = "secretRef";
  @SerializedName(SERIALIZED_NAME_SECRET_REF)
  private V1alpha1CloudShellSpecSecretRef secretRef;

  public static final String SERIALIZED_NAME_TTL_SECONDS_AFTER_STARTED = "ttlSecondsAfterStarted";
  @SerializedName(SERIALIZED_NAME_TTL_SECONDS_AFTER_STARTED)
  private Long ttlSecondsAfterStarted;

  public static final String SERIALIZED_NAME_URL_ARG = "urlArg";
  @SerializedName(SERIALIZED_NAME_URL_ARG)
  private Boolean urlArg;

  public static final String SERIALIZED_NAME_VIRTUAL_SERVICE_CONFIG = "virtualServiceConfig";
  @SerializedName(SERIALIZED_NAME_VIRTUAL_SERVICE_CONFIG)
  private V1alpha1CloudShellSpecVirtualServiceConfig virtualServiceConfig;


  public V1alpha1CloudShellSpec cleanup(Boolean cleanup) {
    
    this.cleanup = cleanup;
    return this;
  }

   /**
   * Cleanup specified whether to delete cloudshell resources when corresponding job status is completed.
   * @return cleanup
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Cleanup specified whether to delete cloudshell resources when corresponding job status is completed.")

  public Boolean getCleanup() {
    return cleanup;
  }


  public void setCleanup(Boolean cleanup) {
    this.cleanup = cleanup;
  }


  public V1alpha1CloudShellSpec commandAction(String commandAction) {
    
    this.commandAction = commandAction;
    return this;
  }

   /**
   * Get commandAction
   * @return commandAction
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getCommandAction() {
    return commandAction;
  }


  public void setCommandAction(String commandAction) {
    this.commandAction = commandAction;
  }


  public V1alpha1CloudShellSpec env(List<V1alpha1CloudShellSpecEnv> env) {
    
    this.env = env;
    return this;
  }

  public V1alpha1CloudShellSpec addEnvItem(V1alpha1CloudShellSpecEnv envItem) {
    if (this.env == null) {
      this.env = new ArrayList<>();
    }
    this.env.add(envItem);
    return this;
  }

   /**
   * List of environment variables to set in the container. Cannot be updated.
   * @return env
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "List of environment variables to set in the container. Cannot be updated.")

  public List<V1alpha1CloudShellSpecEnv> getEnv() {
    return env;
  }


  public void setEnv(List<V1alpha1CloudShellSpecEnv> env) {
    this.env = env;
  }


  public V1alpha1CloudShellSpec exposureMode(ExposureModeEnum exposureMode) {
    
    this.exposureMode = exposureMode;
    return this;
  }

   /**
   * ExposeMode describes how to access ttyd service, either ClusterIP, NodePort, Ingress or VirtualService.
   * @return exposureMode
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "ExposeMode describes how to access ttyd service, either ClusterIP, NodePort, Ingress or VirtualService.")

  public ExposureModeEnum getExposureMode() {
    return exposureMode;
  }


  public void setExposureMode(ExposureModeEnum exposureMode) {
    this.exposureMode = exposureMode;
  }


  public V1alpha1CloudShellSpec image(String image) {
    
    this.image = image;
    return this;
  }

   /**
   * Image defines the image to cloudshell, we can customize an image. Note: the image must be built on top of the officially available base image. Please see: https://github.com/cloudtty/cloudtty#how-to-build-custom-cloudshell-image
   * @return image
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Image defines the image to cloudshell, we can customize an image. Note: the image must be built on top of the officially available base image. Please see: https://github.com/cloudtty/cloudtty#how-to-build-custom-cloudshell-image")

  public String getImage() {
    return image;
  }


  public void setImage(String image) {
    this.image = image;
  }


  public V1alpha1CloudShellSpec ingressConfig(V1alpha1CloudShellSpecIngressConfig ingressConfig) {
    
    this.ingressConfig = ingressConfig;
    return this;
  }

   /**
   * Get ingressConfig
   * @return ingressConfig
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public V1alpha1CloudShellSpecIngressConfig getIngressConfig() {
    return ingressConfig;
  }


  public void setIngressConfig(V1alpha1CloudShellSpecIngressConfig ingressConfig) {
    this.ingressConfig = ingressConfig;
  }


  public V1alpha1CloudShellSpec once(Boolean once) {
    
    this.once = once;
    return this;
  }

   /**
   * accept only one client and exit on disconnection
   * @return once
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "accept only one client and exit on disconnection")

  public Boolean getOnce() {
    return once;
  }


  public void setOnce(Boolean once) {
    this.once = once;
  }


  public V1alpha1CloudShellSpec pathPrefix(String pathPrefix) {
    
    this.pathPrefix = pathPrefix;
    return this;
  }

   /**
   * PathPrefix specified a path prefix to access url, if not, the default path is used.
   * @return pathPrefix
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "PathPrefix specified a path prefix to access url, if not, the default path is used.")

  public String getPathPrefix() {
    return pathPrefix;
  }


  public void setPathPrefix(String pathPrefix) {
    this.pathPrefix = pathPrefix;
  }


  public V1alpha1CloudShellSpec pathSuffix(String pathSuffix) {
    
    this.pathSuffix = pathSuffix;
    return this;
  }

   /**
   * PathSuffix specified a path suffix to access url, if not, the default path is used.
   * @return pathSuffix
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "PathSuffix specified a path suffix to access url, if not, the default path is used.")

  public String getPathSuffix() {
    return pathSuffix;
  }


  public void setPathSuffix(String pathSuffix) {
    this.pathSuffix = pathSuffix;
  }


  public V1alpha1CloudShellSpec secretRef(V1alpha1CloudShellSpecSecretRef secretRef) {
    
    this.secretRef = secretRef;
    return this;
  }

   /**
   * Get secretRef
   * @return secretRef
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public V1alpha1CloudShellSpecSecretRef getSecretRef() {
    return secretRef;
  }


  public void setSecretRef(V1alpha1CloudShellSpecSecretRef secretRef) {
    this.secretRef = secretRef;
  }


  public V1alpha1CloudShellSpec ttlSecondsAfterStarted(Long ttlSecondsAfterStarted) {
    
    this.ttlSecondsAfterStarted = ttlSecondsAfterStarted;
    return this;
  }

   /**
   * Get ttlSecondsAfterStarted
   * @return ttlSecondsAfterStarted
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Long getTtlSecondsAfterStarted() {
    return ttlSecondsAfterStarted;
  }


  public void setTtlSecondsAfterStarted(Long ttlSecondsAfterStarted) {
    this.ttlSecondsAfterStarted = ttlSecondsAfterStarted;
  }


  public V1alpha1CloudShellSpec urlArg(Boolean urlArg) {
    
    this.urlArg = urlArg;
    return this;
  }

   /**
   * UrlArg allow client to send command line arguments in URL (eg: http://localhost:7681?arg&#x3D;foo&amp;arg&#x3D;bar)
   * @return urlArg
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "UrlArg allow client to send command line arguments in URL (eg: http://localhost:7681?arg=foo&arg=bar)")

  public Boolean getUrlArg() {
    return urlArg;
  }


  public void setUrlArg(Boolean urlArg) {
    this.urlArg = urlArg;
  }


  public V1alpha1CloudShellSpec virtualServiceConfig(V1alpha1CloudShellSpecVirtualServiceConfig virtualServiceConfig) {
    
    this.virtualServiceConfig = virtualServiceConfig;
    return this;
  }

   /**
   * Get virtualServiceConfig
   * @return virtualServiceConfig
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public V1alpha1CloudShellSpecVirtualServiceConfig getVirtualServiceConfig() {
    return virtualServiceConfig;
  }


  public void setVirtualServiceConfig(V1alpha1CloudShellSpecVirtualServiceConfig virtualServiceConfig) {
    this.virtualServiceConfig = virtualServiceConfig;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1alpha1CloudShellSpec v1alpha1CloudShellSpec = (V1alpha1CloudShellSpec) o;
    return Objects.equals(this.cleanup, v1alpha1CloudShellSpec.cleanup) &&
        Objects.equals(this.commandAction, v1alpha1CloudShellSpec.commandAction) &&
        Objects.equals(this.env, v1alpha1CloudShellSpec.env) &&
        Objects.equals(this.exposureMode, v1alpha1CloudShellSpec.exposureMode) &&
        Objects.equals(this.image, v1alpha1CloudShellSpec.image) &&
        Objects.equals(this.ingressConfig, v1alpha1CloudShellSpec.ingressConfig) &&
        Objects.equals(this.once, v1alpha1CloudShellSpec.once) &&
        Objects.equals(this.pathPrefix, v1alpha1CloudShellSpec.pathPrefix) &&
        Objects.equals(this.pathSuffix, v1alpha1CloudShellSpec.pathSuffix) &&
        Objects.equals(this.secretRef, v1alpha1CloudShellSpec.secretRef) &&
        Objects.equals(this.ttlSecondsAfterStarted, v1alpha1CloudShellSpec.ttlSecondsAfterStarted) &&
        Objects.equals(this.urlArg, v1alpha1CloudShellSpec.urlArg) &&
        Objects.equals(this.virtualServiceConfig, v1alpha1CloudShellSpec.virtualServiceConfig);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cleanup, commandAction, env, exposureMode, image, ingressConfig, once, pathPrefix, pathSuffix, secretRef, ttlSecondsAfterStarted, urlArg, virtualServiceConfig);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1alpha1CloudShellSpec {\n");
    sb.append("    cleanup: ").append(toIndentedString(cleanup)).append("\n");
    sb.append("    commandAction: ").append(toIndentedString(commandAction)).append("\n");
    sb.append("    env: ").append(toIndentedString(env)).append("\n");
    sb.append("    exposureMode: ").append(toIndentedString(exposureMode)).append("\n");
    sb.append("    image: ").append(toIndentedString(image)).append("\n");
    sb.append("    ingressConfig: ").append(toIndentedString(ingressConfig)).append("\n");
    sb.append("    once: ").append(toIndentedString(once)).append("\n");
    sb.append("    pathPrefix: ").append(toIndentedString(pathPrefix)).append("\n");
    sb.append("    pathSuffix: ").append(toIndentedString(pathSuffix)).append("\n");
    sb.append("    secretRef: ").append(toIndentedString(secretRef)).append("\n");
    sb.append("    ttlSecondsAfterStarted: ").append(toIndentedString(ttlSecondsAfterStarted)).append("\n");
    sb.append("    urlArg: ").append(toIndentedString(urlArg)).append("\n");
    sb.append("    virtualServiceConfig: ").append(toIndentedString(virtualServiceConfig)).append("\n");
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

