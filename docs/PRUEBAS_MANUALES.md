# Pruebas manuales core-admin-service

Primero obtener token desde identity-access-service:

```powershell
$loginBody = @{ username = "admin.core"; password = "password" } | ConvertTo-Json
$loginResponse = Invoke-RestMethod -Method Post -Uri "http://localhost:8081/api/v1/auth/login" -ContentType "application/json" -Body $loginBody
$token = $loginResponse.accessToken
```

Consultar sucursales:

```powershell
Invoke-RestMethod -Method Get -Uri "http://localhost:8083/api/v1/admin/branches" -Headers @{ Authorization = "Bearer $token" }
```

Consultar ventanas operativas:

```powershell
Invoke-RestMethod -Method Get -Uri "http://localhost:8083/api/v1/admin/operational-windows" -Headers @{ Authorization = "Bearer $token" }
```

Consultar IVA:

```powershell
Invoke-RestMethod -Method Get -Uri "http://localhost:8083/api/v1/admin/parameters/IVA_PORCENTAJE" -Headers @{ Authorization = "Bearer $token" }
```

Consultar día hábil:

```powershell
Invoke-RestMethod -Method Get -Uri "http://localhost:8083/api/v1/admin/business-calendar/2026-06-03" -Headers @{ Authorization = "Bearer $token" }
```
