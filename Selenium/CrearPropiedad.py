from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import time

# Configura el driver de Chrome
service = Service('C:/chromedriver-win64/chromedriver.exe')
driver = webdriver.Chrome(service=service)
wait = WebDriverWait(driver, 10)

try:
    # Navega a la página de publicar propiedad
    driver.get("http://localhost:4200/publicar-propiedad")

    # Rellena el formulario de publicación de propiedad
    nombre_input = wait.until(EC.visibility_of_element_located((By.NAME, "nombre")))
    ubicacion_input = wait.until(EC.visibility_of_element_located((By.NAME, "ubicacion")))
    capacidad_input = wait.until(EC.visibility_of_element_located((By.NAME, "capacidad")))
    precio_input = wait.until(EC.visibility_of_element_located((By.NAME, "precioXnoche")))
    parqueadero_checkbox = wait.until(EC.element_to_be_clickable((By.NAME, "parqueadero")))
    piscina_checkbox = wait.until(EC.element_to_be_clickable((By.NAME, "piscina")))
    publicar_button = wait.until(EC.element_to_be_clickable((By.CSS_SELECTOR, "button[type='submit']")))

    nombre_input.send_keys("Propiedad de Prueba")
    ubicacion_input.send_keys("Ciudad de Prueba")
    capacidad_input.send_keys("4")
    precio_input.send_keys("100")
    parqueadero_checkbox.click()
    piscina_checkbox.click()
    publicar_button.click()

    # Espera a que se procese la solicitud
    wait.until(EC.visibility_of_element_located((By.XPATH, "//*[contains(text(), 'Todas tus propiedades')]")))

    # Verifica que la propiedad se haya creado correctamente
    mensaje_exito = driver.find_element(By.XPATH, "//*[contains(text(), 'Todas tus propiedades')]")
    if mensaje_exito.is_displayed():
        print("La propiedad se ha creado correctamente.")
    else:
        print("Error al crear la propiedad.")
except Exception as e:
    print(f"Error: {e}")
    driver.save_screenshot('error_screenshot.png')  # Guarda una captura de pantalla del error
finally:
    # Cierra el navegador
    driver.quit()