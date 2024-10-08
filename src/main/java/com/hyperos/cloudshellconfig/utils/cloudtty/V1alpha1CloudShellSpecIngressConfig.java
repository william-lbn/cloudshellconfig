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

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * Specifies a port number range 30000-32767 when using nodeport mode, if not specified, kubernetes default random rule is used. NodePort int32 &#x60;json:\&quot;NodePort,omitempty\&quot;&#x60; IngressConfig specifies necessary parameters to create ingress.
 */
@ApiModel(description = "Specifies a port number range 30000-32767 when using nodeport mode, if not specified, kubernetes default random rule is used. NodePort int32 `json:\"NodePort,omitempty\"` IngressConfig specifies necessary parameters to create ingress.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-09-23T07:59:07.811Z[Etc/UTC]")
public class V1alpha1CloudShellSpecIngressConfig {
  public static final String SERIALIZED_NAME_INGRESS_CLASS_NAME = "ingressClassName";
  @SerializedName(SERIALIZED_NAME_INGRESS_CLASS_NAME)
  private String ingressClassName;

  public static final String SERIALIZED_NAME_INGRESS_NAME = "ingressName";
  @SerializedName(SERIALIZED_NAME_INGRESS_NAME)
  private String ingressName;

  public static final String SERIALIZED_NAME_NAMESPACE = "namespace";
  @SerializedName(SERIALIZED_NAME_NAMESPACE)
  private String namespace;


  public V1alpha1CloudShellSpecIngressConfig ingressClassName(String ingressClassName) {
    
    this.ingressClassName = ingressClassName;
    return this;
  }

   /**
   * IngressClassName specifies a ingress controller to ingress, it must be fill when the cluster have multiple ingress controller service.
   * @return ingressClassName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "IngressClassName specifies a ingress controller to ingress, it must be fill when the cluster have multiple ingress controller service.")

  public String getIngressClassName() {
    return ingressClassName;
  }


  public void setIngressClassName(String ingressClassName) {
    this.ingressClassName = ingressClassName;
  }


  public V1alpha1CloudShellSpecIngressConfig ingressName(String ingressName) {
    
    this.ingressName = ingressName;
    return this;
  }

   /**
   * IngressName specifies a name to ingress, if it&#39;s empty, default \&quot;cloudshell-ingress\&quot;.
   * @return ingressName
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "IngressName specifies a name to ingress, if it's empty, default \"cloudshell-ingress\".")

  public String getIngressName() {
    return ingressName;
  }


  public void setIngressName(String ingressName) {
    this.ingressName = ingressName;
  }


  public V1alpha1CloudShellSpecIngressConfig namespace(String namespace) {
    
    this.namespace = namespace;
    return this;
  }

   /**
   * Namespace specifies a namespace that the virtualService will be created in it. if it&#39;s empty, default the cloudshell namespace.
   * @return namespace
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Namespace specifies a namespace that the virtualService will be created in it. if it's empty, default the cloudshell namespace.")

  public String getNamespace() {
    return namespace;
  }


  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1alpha1CloudShellSpecIngressConfig v1alpha1CloudShellSpecIngressConfig = (V1alpha1CloudShellSpecIngressConfig) o;
    return Objects.equals(this.ingressClassName, v1alpha1CloudShellSpecIngressConfig.ingressClassName) &&
        Objects.equals(this.ingressName, v1alpha1CloudShellSpecIngressConfig.ingressName) &&
        Objects.equals(this.namespace, v1alpha1CloudShellSpecIngressConfig.namespace);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ingressClassName, ingressName, namespace);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1alpha1CloudShellSpecIngressConfig {\n");
    sb.append("    ingressClassName: ").append(toIndentedString(ingressClassName)).append("\n");
    sb.append("    ingressName: ").append(toIndentedString(ingressName)).append("\n");
    sb.append("    namespace: ").append(toIndentedString(namespace)).append("\n");
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

