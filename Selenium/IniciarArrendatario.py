from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time

# Configura el driver de Chrome
service = Service('C:/chromedriver-win64/chromedriver.exe')
driver = webdriver.Chrome(service=service)
wait = WebDriverWait(driver, 10)

try:
    # Navega a la página de inicio de sesión
    driver.get("http://localhost:4200/login")

    # Espera a que el título de la página de acceso sea visible
    wait.until(EC.visibility_of_element_located((By.XPATH, "//h2[contains(text(), 'Acceso')]")))

    # Selecciona el rol de arrendador
    arrendador_radio = wait.until(EC.element_to_be_clickable((By.XPATH, "//input[@type='radio' and @value='arrendatario']")))
    arrendador_radio.click()

    # Inicia sesión como arrendador
    usuario_input = wait.until(EC.visibility_of_element_located((By.NAME, "usuario")))
    contrasena_input = wait.until(EC.visibility_of_element_located((By.NAME, "password")))
    login_button = wait.until(EC.element_to_be_clickable((By.CSS_SELECTOR, "button[type='submit']")))

    usuario_input.send_keys("arrendador")
    contrasena_input.send_keys("password")
    login_button.click()

    # Espera a que se cargue la página de inicio del arrendador
    wait.until(EC.url_contains("arrendatario"))
    
    print("Inicio de sesión exitoso.")
except Exception as e:
    print(f"Error: {e}")
    driver.save_screenshot('error_screenshot.png')  # Guarda una captura de pantalla del error
finally:
    # Cierra el navegador
    driver.quit()