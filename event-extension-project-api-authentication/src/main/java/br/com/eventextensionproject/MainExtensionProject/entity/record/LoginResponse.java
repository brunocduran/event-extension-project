package br.com.eventextensionproject.MainExtensionProject.entity.record;

import br.com.eventextensionproject.MainExtensionProject.entity.enumarator.RolePermission;
import java.util.Set;

public record LoginResponse(String token, String name, Set<RolePermission> rolePermission) {
}
