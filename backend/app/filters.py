from app.config import settings



def get_filter_details():
    image_filters = {
        "oil_paint": {
            "name": "oil_paint",
            "display_image": "https://images.unsplash.com/photo-1558865869-c93f6f8482af?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1181&q=80"
        },
        "stylized": {
            "name": "stylize",
            "display_image": "https://www.befunky.com/images/prismic/32083dff-734b-49a7-bb4d-c0dc512401af_hero-photo-effects-5.jpg?auto=avif,webp&format=jpg&width=896"
        },
        "pencil_sketch": {
            "name": "pencil_sketch",
            "display_image": "https://images.unsplash.com/photo-1568205612837-017257d2310a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1180&q=80"
        }
    }

    return image_filters