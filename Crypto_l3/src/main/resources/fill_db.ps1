# Простой скрипт для заполнения БД
$env:PGPASSWORD = "1234"
$psql = "C:\Program Files\PostgreSQL\18\bin\psql.exe"

Write-Host "Creating tables..." -ForegroundColor Yellow
& $psql -U postgres -h localhost -d cryptodb -f schema.sql

Write-Host "Filling data..." -ForegroundColor Yellow
& $psql -U postgres -h localhost -d cryptodb -f data.sql

Write-Host "Done!" -ForegroundColor Green